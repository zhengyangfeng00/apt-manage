// Copyright 2012 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// //////////////////////////////////////////////////////////////////////////////

package org.zhengyang.aptmanagement.shared.dto;

import java.io.Serializable;

import javax.persistence.Transient;

import com.google.common.base.Objects;




/**
 * An instance of this class must have an immutable ID.
 * That ID is used to define equals and hashCode.
 * 
 * Always override equals and hashCode together. Always! 
 * Read why here: http://www.javamex.com/tutorials/collections/hash_code_equals.shtml
 * <br>
 * To help you override them correctly, 
 * use {@link com.google.common.base.Objects#equal(Object, Object)} and 
 * {@link com.google.common.base.Objects#hashCode(Object...)}.
 * <br>
 * See: http://code.google.com/p/guava-libraries/wiki/CommonObjectUtilitiesExplained
 * <br>
 * Overriding equality and hashCode is useful for testing.
 * 
 * @author yzibin@google.com (Yoav Zibin)
 */
public abstract class Equality implements Serializable {
  private static final long serialVersionUID = 1L;

  @Transient private transient Object cacheId;
  @Transient private transient int cacheHashId;
  
  private Object getCacheId() {
    if (cacheId != null) {
      return cacheId;
    }
    cacheId = getId();
    cacheHashId = cacheId == null ? 0 : cacheId.hashCode();
    return cacheId;
  }
  
  public abstract Object getId();

  @Override
  public final boolean equals(Object other) {
    if (!(other instanceof Equality)) {
      return false;
    }
    Object id = getCacheId();
    return id == null ? this == other : Objects.equal(id, ((Equality) other).getId());
  }

  @Override
  public final int hashCode() {
    getCacheId();
    return cacheHashId;
  }
}
