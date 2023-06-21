/*
 * Copyright 2008-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.mongodb.spark.sql.connector.read;

import java.util.Objects;

import org.apache.spark.sql.connector.read.streaming.PartitionOffset;

import org.bson.BsonDocument;

import com.mongodb.spark.sql.connector.assertions.Assertions;

/** A resume token partition offset */
final class MongoContinuousInputPartitionOffset implements PartitionOffset {
  private static final long serialVersionUID = 1L;
  private final ResumeTokenBasedOffset offset;

  /**
   * Construct a new instance
   *
   * @param resumeToken the change stream resume token
   */
  MongoContinuousInputPartitionOffset(final ResumeTokenBasedOffset offset) {
    Assertions.ensureArgument(() -> offset != null, () -> "Invalid offset");
    this.offset = offset;
  }

  BsonDocument getResumeToken() {
    return offset.getResumeToken();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final MongoContinuousInputPartitionOffset that = (MongoContinuousInputPartitionOffset) o;
    return Objects.equals(getResumeToken(), that.getResumeToken());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getResumeToken());
  }

  @Override
  public String toString() {
    return "MongoContinuousInputPartitionOffset{resumeToken="
        + offset.getResumeToken().toJson()
        + '}';
  }
}