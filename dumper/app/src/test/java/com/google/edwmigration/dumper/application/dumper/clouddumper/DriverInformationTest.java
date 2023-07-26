/*
 * Copyright 2022-2023 Google LLC
 * Copyright 2013-2021 CompilerWorks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.edwmigration.dumper.application.dumper.clouddumper;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import org.junit.Test;

public class DriverInformationTest {

  @Test
  public void getDriverFileName_rootPath_success() throws Exception {
    assertEquals(
        "foo.zip",
        DriverInformation.builder("test", new URI("http://google.com/foo.zip"))
            .build()
            .getDriverFileName());
  }

  @Test
  public void getDriverFileName_complexUri_success() throws Exception {
    assertEquals(
        "foo",
        DriverInformation.builder("test", new URI("http://google.com/some/path/foo?q=1#bar"))
            .build()
            .getDriverFileName());
  }
}
