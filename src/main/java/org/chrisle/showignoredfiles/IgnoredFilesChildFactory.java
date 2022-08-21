package org.chrisle.showignoredfiles;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
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
    protected boolean createKeys(List<File> projectFiles) {
        final File file = new File(this.project.getProjectDirectory().getPath());
//        File[] files = file.listFiles((File pathname) -> {
//            final String filesRegEx = NbPreferences.root().node("org/netbeans/core").get("IgnoredFiles", null);
//
//            if (filesRegEx != null && !filesRegEx.isEmpty()) {
//                return pathname.getName().matches(filesRegEx);
//            }
//
//            return false;
//        });
        projectFiles.clear();

        projectFiles.addAll(Arrays.asList(file.listFiles()));

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
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Da ging was schief");
            result = new AbstractNode(Children.LEAF, Lookups.singleton(key));
        }

        return result;
    }
}
