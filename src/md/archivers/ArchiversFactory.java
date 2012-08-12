package md.archivers;

import java.util.*;
import md.model.Archiver;

/**
 *
 * @author Hernan
 */
public class ArchiversFactory {
    //TODO API Armar esquema de registración
    public static Map<String, Archiver> archiversMap() {
        Map<String, Archiver> result = new LinkedHashMap<String, Archiver>();
        result.put(".cbz", new ZIPArchiver(".cbz"));
        result.put(".zip", new ZIPArchiver(".zip"));
        result.put(".cbr", new RARArchiver(".cbr"));
        result.put(".rar", new RARArchiver(".rar"));
        return result;
    }

    public static List<Archiver> listArchivers() {
        List<Archiver> result = new ArrayList<Archiver>();
        result.addAll(ZIPArchiver.createArchivers());
        result.addAll(RARArchiver.createArchivers());
        return result;
    }


}
