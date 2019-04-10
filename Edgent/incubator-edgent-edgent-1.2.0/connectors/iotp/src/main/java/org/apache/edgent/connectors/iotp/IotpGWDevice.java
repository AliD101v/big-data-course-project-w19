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

package org.apache.edgent.connectors.iotp;

import org.apache.edgent.connectors.iot.IotDevice;
import org.apache.edgent.connectors.iotp.runtime.IotpGWConnector;
import org.apache.edgent.connectors.iotp.runtime.IotpGWDeviceEventsFixed;
import org.apache.edgent.connectors.iotp.runtime.IotpGWDeviceEventsFunction;
import org.apache.edgent.function.Function;
import org.apache.edgent.function.UnaryOperator;
import org.apache.edgent.topology.TSink;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;

import com.google.gson.JsonObject;

/**
 * An IotDevice connected to WIoTP via a Gateway device.
 * <P>
 * This is a non-public part of the IotpGateway implementation.
 * 
 * @see IotpGateway#getIotDevice(java.util.Map)
 * @see IotpGateway#getIotDevice(java.util.Map)
 */
class IotpGWDevice implements IotDevice {
  
  private final IotpGateway gateway;
  private final IotpGWConnector connector;
  private final Topology topology;
  private final String fqDeviceId;
  private final String deviceType;
  
  IotpGWDevice(IotpGateway gw, IotpGWConnector connector, Topology topology, String fqDeviceId) {
    this.gateway = gw;
    this.connector = connector;
    this.topology = topology;
    this.fqDeviceId = fqDeviceId;
    String[] devIdToks = IotpGWConnector.splitFqDeviceId(fqDeviceId);
    this.deviceType = devIdToks[0];
  }

  @Override
  public Topology topology() {
    return topology;
  }

  @Override
  public String getDeviceType() {
    return deviceType;
  }

  @Override
  public String getDeviceId() {
    return fqDeviceId;
  }

  @Override
  public TSink<JsonObject> events(TStream<JsonObject> stream, Function<JsonObject, String> eventId,
      UnaryOperator<JsonObject> payload, Function<JsonObject, Integer> qos) {
    return stream.sink(
        new IotpGWDeviceEventsFunction(connector, jo -> fqDeviceId, eventId, payload, qos));
  }

  @Override
  public TSink<JsonObject> events(TStream<JsonObject> stream, String eventId, int qos) {
    return stream.sink(new IotpGWDeviceEventsFixed(connector, fqDeviceId, eventId, qos));
  }

  @Override
  public TStream<JsonObject> commands(String... commands) {
    return gateway.commandsForDevice(fqDeviceId, commands);
  }
  
  @Override
  public boolean equals(Object o2) {
    return o2 == this 
        || (o2 instanceof IotpGWDevice && ((IotpGWDevice)o2).fqDeviceId.equals(fqDeviceId));
  }

  @Override
  public int hashCode() {
    return fqDeviceId.hashCode();
  }
  
  @Override
  public String toString() {
    return String.format("IotpGWDevice %s", fqDeviceId); 
  }
  
}
