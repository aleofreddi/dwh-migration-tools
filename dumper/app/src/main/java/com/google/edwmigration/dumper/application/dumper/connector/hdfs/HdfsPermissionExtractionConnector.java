/*
 * Copyright 2022-2024 Google LLC
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
package com.google.edwmigration.dumper.application.dumper.connector.hdfs;

import com.google.auto.service.AutoService;
import com.google.edwmigration.dumper.application.dumper.ConnectorArguments;
import com.google.edwmigration.dumper.application.dumper.annotations.RespectsInput;
import com.google.edwmigration.dumper.application.dumper.connector.AbstractConnector;
import com.google.edwmigration.dumper.application.dumper.connector.Connector;
import com.google.edwmigration.dumper.application.dumper.handle.Handle;
import com.google.edwmigration.dumper.application.dumper.task.DumpMetadataTask;
import com.google.edwmigration.dumper.application.dumper.task.FormatTask;
import com.google.edwmigration.dumper.application.dumper.task.Task;
import com.google.edwmigration.dumper.application.dumper.utils.ArchiveNameUtil;
import com.google.edwmigration.dumper.plugin.ext.jdk.annotation.Description;
import com.google.edwmigration.dumper.plugin.lib.dumper.spi.HdfsPermissionExtractionDumpFormat;
import java.time.Clock;
import java.util.List;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RespectsInput(
    order = 100,
    arg = ConnectorArguments.OPT_HOST,
    description = "HDFS cluster host.",
    defaultValue = ConnectorArguments.OPT_HOST_DEFAULT)
@RespectsInput(
    order = 101,
    arg = ConnectorArguments.OPT_PORT,
    description = "HDFS cluster port.",
    defaultValue = ConnectorArguments.OPT_HDFS_PORT_DEFAULT)
@RespectsInput(
    order = 500,
    arg = ConnectorArguments.OPT_THREAD_POOL_SIZE,
    description = "The size of the thread pool to use when extracting hdfs permissions.")
@AutoService({Connector.class})
@Description("Dumps permissions from the HDFS.")
public class HdfsPermissionExtractionConnector extends AbstractConnector
    implements HdfsPermissionExtractionDumpFormat {
  static final Logger LOG = LoggerFactory.getLogger(HdfsPermissionExtractionConnector.class);

  public HdfsPermissionExtractionConnector() {
    super("hdfs-permissions");
  }

  @Nonnull
  @Override
  public String getDefaultFileName(boolean isAssessment, Clock clock) {
    return ArchiveNameUtil.getFileName(getName());
  }

  @Override
  public void addTasksTo(@Nonnull List<? super Task<?>> out, @Nonnull ConnectorArguments args)
      throws Exception {
    out.add(new DumpMetadataTask(args, FORMAT_NAME));
    out.add(new FormatTask(FORMAT_NAME));

    out.add(new HdfsPermissionExtractionTask(args));
    out.add(new HdfsContentSummaryTask(args));
  }

  @Nonnull
  @Override
  public Handle open(@Nonnull ConnectorArguments arguments) throws Exception {
    return new HdfsHandle(arguments);
  }
}
