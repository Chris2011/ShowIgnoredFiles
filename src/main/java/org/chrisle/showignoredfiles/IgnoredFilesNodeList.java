/*
 * Copyright 2021 Chris.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.chrisle.showignoredfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import java.util.regex.Pattern;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.Sources;
import org.netbeans.spi.project.ui.support.NodeList;
import org.openide.loaders.DataFilter;
import org.openide.loaders.DataObject;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.ChangeSupport;
import org.openide.util.NbPreferences;
import org.openide.util.WeakListeners;

/**
 *
 * @author Chris
 */
public final class IgnoredFilesNodeList implements NodeList<Node>, ChangeListener, DataFilter {

    private static final long serialVersionUID = 2484908092068609446L;
    private final Sources projectSources;
    private final ChangeSupport changeSupport = new ChangeSupport(this);
    private final Project project;
    private final ChangeListener changeListener;

    public IgnoredFilesNodeList(Project project) {
        this.project = project;
        projectSources = ProjectUtils.getSources(project);
        changeListener = WeakListeners.change(this, projectSources);

        getIgnoredFiles();
    }

    private PreferenceChangeListener preferencesListener = null;

    private static final String PROP_IGNORED_FILES = "IgnoredFiles"; // NOI18N
    private static final String PROP_IGNORE_HIDDEN_FILES_IN_USER_HOME
            = "IgnoreHiddenFilesInUserHome";

//    private Pattern ignoreFilesPattern = null;
//
//    private boolean ignoreHiddenInHomeInitialized = false;

    private void getIgnoredFiles() {
        PreferenceChangeListener listenerToAdd;

        synchronized (this) {
            if (preferencesListener == null) {
                preferencesListener = (PreferenceChangeEvent evt) -> {
                    if (PROP_IGNORED_FILES.equals(evt.getKey())) {
//                        ignoreFilesPattern = null;
                        
                        changeSupport.fireChange();
                    } else if (PROP_IGNORE_HIDDEN_FILES_IN_USER_HOME.equals(
                            evt.getKey())) {
//                        ignoreHiddenInHomeInitialized = false;
                        changeSupport.fireChange();
                    }
                };
                listenerToAdd = preferencesListener;
            } else {
                listenerToAdd = null;
            }
        }
        if (listenerToAdd != null) {
            getPreferences().addPreferenceChangeListener(listenerToAdd);
        }
    }

    private static Preferences getPreferences() {
        return NbPreferences.root().node("/org/netbeans/core");
    }

    @Override
    public List<Node> keys() {
        List<Node> result = new ArrayList<>();

        result.add(new IgnoredFilesNode(project));

        return result;
    }

    @Override
    public void addChangeListener(ChangeListener cl) {
        changeSupport.addChangeListener(cl);
    }

    @Override
    public void removeChangeListener(ChangeListener cl) {
        changeSupport.removeChangeListener(cl);
    }

    @Override
    public Node node(Node node) {
        return new FilterNode(node);
    }

    @Override
    public void addNotify() {
        projectSources.addChangeListener(changeListener);
    }

    @Override
    public void removeNotify() {
        projectSources.removeChangeListener(changeListener);
    }

    @Override
    public void stateChanged(final ChangeEvent e) {
        SwingUtilities.invokeLater(() -> {
            fireChanged();
        });
    }

    private void fireChanged() {
        changeSupport.fireChange();
    }

    @Override
    public boolean acceptDataObject(DataObject d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
