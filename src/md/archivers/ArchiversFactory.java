/* MangaDownloader can help with Comics too
   Copyright (C) 2012 Hernán Parra

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
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
