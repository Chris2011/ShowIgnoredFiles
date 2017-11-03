package org.chrisle.showignoredfiles;

import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.Sources;
import org.netbeans.spi.project.ui.support.NodeFactory;
import org.netbeans.spi.project.ui.support.NodeList;
import org.openide.loaders.DataFilter;
import org.openide.loaders.DataObject;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.ChangeSupport;
import org.openide.util.WeakListeners;

/**
 *
 * @author Chrl
 */
@NodeFactory.Registration(projectType = {
    "org-netbeans-modules-ant-freeform",
    "org-netbeans-modules-apisupport-project",
    "org-netbeans-modules-apisupport-project-suite",
    "org-netbeans-modules-apisupport-project-suite-jnlp",
    "org-netbeans-modules-apisupport-project-suite-osgi",
    "org-netbeans-modules-apisupport-project-suite-package",
    "org-netbeans-modules-autoproject",
    "org-netbeans-modules-bpel-project",
    "org-netbeans-modules-cnd-api-project",
    "org-netbeans-gradle-project",
    "org-netbeans-modules-gradle-project",
    "org-netbeans-modules-groovy-grailsproject",
    "org-netbeans-modules-j2ee-clientproject",
    "org-netbeans-modules-j2ee-earproject",
    "org-netbeans-modules-j2ee-ejbjarproject",
    "org-netbeans-modules-j2me-project",
    "org-netbeans-modules-java-j2seproject",
    "org-netbeans-modules-javacard-capproject",
    "org-netbeans-modules-javacard-clslibproject",
    "org-netbeans-modules-javacard-eapproject",
    "org-netbeans-modules-javacard-extlibproject",
    "org-netbeans-modules-javacard-project",
    "org-netbeans-modules-javacard-webproject",
    "org-netbeans-modules-javaee-project",
    "org-netbeans-modules-javafx2-project",
    "org-netbeans-modules-maven",
    "org-netbeans-modules-mobility-project",
    "org-netbeans-modules-php-phpproject",
    "org-netbeans-modules-php-project",
    "org-netbeans-modules-ruby-project",
    "org-netbeans-modules-scala-project",
    "org-netbeans-modules-sql-project",
    "org-netbeans-modules-web-clientproject",
    "org-netbeans-modules-web-project",
    "org-netbeans-modules-xslt-project",
    "org-netbeans-modules-scala-sbt",
    "org-netbeans-modules-cnd-makeproject",
    "org.netbeans.modules.web.clientproject"
}, position = 10000)
public class IgnoredFilesNodeFactory implements NodeFactory {
    @Override
    public NodeList<?> createNodes(final Project prjct) {
        return new IgnoredFilesNodeList(prjct);
    }

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
        }

        @Override
        public List<Node> keys() {
            List<Node> result = new ArrayList<Node>();

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
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    fireChanged();
                }
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
}