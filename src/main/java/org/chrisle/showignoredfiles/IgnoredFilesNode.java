package org.chrisle.showignoredfiles;

import java.awt.Image;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.project.Project;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;

/**
 *
 * @author Chrl
 */
public class IgnoredFilesNode extends AbstractNode {
    @StaticResource
    private static final String IMAGE = "org/chrisle/showignoredfiles/eye-slash-8x8.png";

    public IgnoredFilesNode(Project project) {
        super(Children.create(new IgnoredFilesChildFactory(project), true));

        setDisplayName("Ignored Files & Folders");
    }

    @Override
    public Image getIcon(int type) {
        return ImageUtilities.mergeImages(ImageUtilities.loadImage("org/netbeans/modules/openfile/folder.gif"), ImageUtilities.loadImage(IMAGE), 7, 7);
    }

    @Override
    public Image getOpenedIcon(int type) {
        return ImageUtilities.mergeImages(ImageUtilities.loadImage("org/netbeans/modules/openfile/folder.gif"), ImageUtilities.loadImage(IMAGE), 7, 7);
    }
}
