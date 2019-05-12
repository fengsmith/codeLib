import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author:shifengqiang
 * @Date:19/5/12 上午11:32
 */
public class DeleteDuplicateFile {
    public static void main(String[] args) throws Exception {
        String path = "";
        File root = new File(path);
        Set<String> hashSet = new HashSet<String>();
        traverseDelete(root, hashSet);


    }

    public static void traverseDelete(File root, Set<String> md5Set) throws Exception {
        if (root.isFile()) {
            FileInputStream inputStream = new FileInputStream(root);
            String md5 = DigestUtils.md5Hex(inputStream);
            if (md5Set.contains(md5)) {
                root.delete();
            } else {
                md5Set.add(md5);
            }

        } else {
            for (File file : root.listFiles()) {
                traverseDelete(file, md5Set);

            }
        }
    }
}
