/*
 * Copyright Terracotta, Inc.
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
package org.ehcache.clustered.server.management;

import org.terracotta.management.model.capabilities.descriptors.Descriptor;
import org.terracotta.management.model.capabilities.descriptors.Settings;
import org.terracotta.management.model.context.Context;
import org.terracotta.management.registry.Named;
import org.terracotta.management.registry.RequiredContext;
import org.terracotta.management.service.monitoring.registry.provider.AliasBindingManagementProvider;

import java.util.Collection;
import java.util.Collections;

@Named("ClusteredTierManagerSettings")
@RequiredContext({@Named("consumerId"), @Named("type"), @Named("alias")})
public class ClusteredTierManagerSettingsManagementProvider extends AliasBindingManagementProvider<ClusteredTierManagerBinding> {

  public ClusteredTierManagerSettingsManagementProvider() {
    super(ClusteredTierManagerBinding.class);
  }

  @Override
  protected ExposedAliasBinding<ClusteredTierManagerBinding> internalWrap(final Context context, final ClusteredTierManagerBinding managedObject) {
    return new ExposedClusteredTierManagerBinding(context, managedObject);
  }

  private static class ExposedClusteredTierManagerBinding extends ExposedAliasBinding<ClusteredTierManagerBinding> {

    public ExposedClusteredTierManagerBinding(final Context context, final ClusteredTierManagerBinding binding) {
      super(context, binding);
    }

    @Override
    public Context getContext() {
      return super.getContext().with("type", "ClusteredTierManager");
    }

    @Override
    public Collection<? extends Descriptor> getDescriptors() {
      Settings settings = new Settings(getContext()).set("defaultServerResource", getBinding().getValue().getDefaultServerResource());
      return Collections.singleton(settings);
    }
  }
}
