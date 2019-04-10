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
package org.apache.edgent.connectors.iot;

/**
 * Device command identifiers used by Edgent.
 * 
 * @see IotDevice#RESERVED_ID_PREFIX
 */
public interface Commands {
    
    /**
     * Command identifier used for the control service.
     * <BR>
     * The command payload is used to invoke operations
     * against control MBeans using an instance of
     * {@code org.apache.edgent.runtime.jsoncontrol.JsonControlService}.
     * <BR>
     * Value is {@value}.
     * 
     * @see org.apache.edgent.execution.services.ControlService
     * See {@code org.apache.edgent.providers.iot.IotProvider}
     */
    String CONTROL_SERVICE = IotDevice.RESERVED_ID_PREFIX + "Control";

}
