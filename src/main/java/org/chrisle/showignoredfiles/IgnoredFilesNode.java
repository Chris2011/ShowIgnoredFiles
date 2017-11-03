package org.chrisle.showignoredfiles;

import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.project.Project;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 *
 * @author Chrl
 */
public class IgnoredFilesNode extends AbstractNode {
    @StaticResource
    private static final String IMAGE = "org/chrisle/showignoredfiles/folder.png";

    public IgnoredFilesNode(Project project) {
           super(Children.create(new IgnoredFilesChildFactory(project), true));

        setDisplayName("Ignored Files & Folders");
        setIconBaseWithExtension(IMAGE);
    }
}