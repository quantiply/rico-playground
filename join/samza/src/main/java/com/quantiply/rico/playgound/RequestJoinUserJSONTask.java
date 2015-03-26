package com.quantiply.rico.playgound;

import org.apache.samza.config.Config;
import org.apache.samza.storage.kv.KeyValueStore;
import org.apache.samza.system.IncomingMessageEnvelope;
import org.apache.samza.system.OutgoingMessageEnvelope;
import org.apache.samza.system.SystemStream;
import org.apache.samza.task.*;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class RequestJoinUserJSONTask implements InitableTask, StreamTask {
    private String userStreamName;
    private String requestStreamName;
    private SystemStream outStream;
    private KeyValueStore<Integer, Object> userStore;

    @Override
    public void init(Config config, TaskContext context) throws Exception {
        userStreamName = config.get("topics.users");
        requestStreamName = config.get("topics.requests");
        outStream = new SystemStream("kafka", config.get("topics.join"));
        userStore = (KeyValueStore<Integer, Object>) context.getStore("users");
    }

    @Override
    public void process(IncomingMessageEnvelope envelope, MessageCollector collector, TaskCoordinator coordinator) throws Exception {
        String stream = envelope.getSystemStreamPartition().getSystemStream().getStream();

        if (stream.equals(userStreamName)) {
            processUserMsg(envelope, collector, coordinator);
        }
        else if (stream.equals(requestStreamName)) {
            processRequestMsg(envelope, collector, coordinator);
        }
        else {
            throw new InputMismatchException("Unknown stream: " + stream);
        }
    }

    private void processRequestMsg(IncomingMessageEnvelope envelope, MessageCollector collector, TaskCoordinator coordinator) {
        Map<String, Object> request = (Map<String, Object>) envelope.getMessage();
        Integer user_id = (Integer)request.get("user_id");
        Map<String, Object> user = (Map<String, Object>) userStore.get(user_id);

        Map<String, Object> joined = new HashMap<String, Object>();
        joined.putAll(request);
        request.put("user_name", user.get("name"));
        request.put("user_email", user.get("email"));
        request.put("user_phone", user.get("phone_number"));

        collector.send(new OutgoingMessageEnvelope(outStream, null, null, joined));
    }

    private void processUserMsg(IncomingMessageEnvelope envelope, MessageCollector collector, TaskCoordinator coordinator) {
        Map<String, Object> user = (Map<String, Object>) envelope.getMessage();
        userStore.put((Integer)user.get("id"), envelope.getMessage());
    }
}
