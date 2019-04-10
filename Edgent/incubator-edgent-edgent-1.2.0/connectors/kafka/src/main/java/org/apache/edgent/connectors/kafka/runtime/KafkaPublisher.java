/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
*/

package org.apache.edgent.connectors.kafka.runtime;

import org.apache.edgent.function.Consumer;
import org.apache.edgent.function.Function;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;

/**
 * A publisher of Kafka key/value records from a stream of tuples of type {@code T}
 *
 * @param <T> tuple type
 */
public class KafkaPublisher<T> implements Consumer<T>, AutoCloseable {
    private static final long serialVersionUID = 1L;
    private static final Logger trace = KafkaProducerConnector.getTrace();
    private final Function<T, byte[]> keyFn;
    private final Function<T, byte[]> valueFn;
    private final Function<T, String> topicFn;
    private final Function<T, Integer> partitionFn;
    private String id;
    private KafkaProducerConnector connector;

    public KafkaPublisher(KafkaProducerConnector connector, Function<T, byte[]> keyFn,
            Function<T, byte[]> valueFn, Function<T, String> topicFn,
            Function<T, Integer> partitionFn) {
        
        this.connector = connector;
        if (keyFn == null)
            keyFn = tuple -> null;
        this.keyFn = keyFn;
        this.valueFn = valueFn;
        this.topicFn = topicFn;
        if (partitionFn == null)
            partitionFn = tuple -> null;
        this.partitionFn = partitionFn;
    }

    @Override
    public void accept(T t) {
        String topic = topicFn.apply(t);
        Integer partition = partitionFn.apply(t);
        byte[] key = keyFn.apply(t);
        byte[] value = valueFn.apply(t);
        ProducerRecord<byte[],byte[]> rec = new ProducerRecord<>(
                topic, partition, key, value);

        trace.trace("{} sending rec to topic:{} partition:{}", id(), topic, partition);
        
        // TODO add callback for trace of actual completion?
        
        connector.client().send(rec);  // async; doesn't throw
    }

    @Override
    public void close() throws Exception {
        connector.close();
    }
    
    private String id() {
        if (id == null) {
            // include our short object Id
            id = connector.id() + " PUB " + toString().substring(toString().indexOf('@') + 1);
        }
        return id;
    }
}
