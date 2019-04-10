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
package org.apache.edgent.topology.mbeans;

import org.apache.edgent.topology.services.ApplicationService;
import org.apache.edgent.topology.services.TopologyBuilder;

/**
 * Control MBean for the application service.
 * 
 * @see ApplicationService
 */
public interface ApplicationServiceMXBean {
    
    String TYPE = "appService";
    
    /**
     * Submit an application registered with the application service.
     * 
     * @param applicationName Name of the application.
     * @param jsonConfig JsonObject configuration serialized as a JSON String.
     * Null or an empty String is equivalent to an empty JSON object.
     * 
     * @throws Exception Error submitting application.
     * @see ApplicationService
     */
    void submit(String applicationName, String jsonConfig) throws Exception;
    
    /**
     * Register a jar file containing applications with
     * the application service.
     * 
     * <p>See {@link ApplicationService#registerJar(String, String) ApplicationService.registerJar()}
     * for further semantics and requirements on the jar.
     * 
     * @param jarURL URL for the jar file.
     * @param jsonConfig JsonObject configuration serialized as a JSON String.
     * Null or an empty String is equivalent to an empty JSON object.
     * @throws Exception Error registering jar.
     * 
     * @see ApplicationService
     * @see TopologyBuilder
     */
    void registerJar(String jarURL, String jsonConfig) throws Exception;
}
