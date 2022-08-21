package org.chrisle.showignoredfiles;

import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ui.support.NodeFactory;
import org.netbeans.spi.project.ui.support.NodeList;

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
    "org.netbeans.modules.web.clientproject",
    "adhoc-projects",
    "adhoc.projects",
    "com.timboudreau.adhoc.project",
    "org-netbeans-modules-profiler"
}, position = 10000)
public class IgnoredFilesNodeFactory implements NodeFactory {
    @Override
    public NodeList<?> createNodes(final Project prjct) {
        return new IgnoredFilesNodeList(prjct);
    }
}
