package org.chrisle.showignoredfiles;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.NbPreferences;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Chrl
 */
public class IgnoredFilesChildFactory extends ChildFactory<File> {
    private final Project project;

    public IgnoredFilesChildFactory(Project project) {
        this.project = project;
    }

    @Override
    protected boolean createKeys(List<File> projectDir) {
        File[] files = new File(this.project.getProjectDirectory().getPath()).listFiles((File pathname) -> {
            System.out.println(pathname);
            System.out.println(pathname.getName());
            System.out.println(String.valueOf(pathname.getName().matches(NbPreferences.root().node("org/netbeans/core").get("IgnoredFiles", null))));

            return pathname.getName().matches(NbPreferences.root().node("org/netbeans/core").get("IgnoredFiles", null));
        });

        projectDir.addAll(Arrays.asList(files));

        return true;
    }

    @Override
    protected Node createNodeForKey(File key) {
        Node result = null;

        try {
            DataObject dataObject = DataObject.find(FileUtil.toFileObject(key));

            result = dataObject.getNodeDelegate();
            result.setDisplayName(key.getName());

        } catch (DataObjectNotFoundException ex) {
            result = new AbstractNode(Children.LEAF, Lookups.singleton(key));
        }

        return result;
    }
}