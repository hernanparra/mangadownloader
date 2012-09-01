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
package md.parsers;

import java.util.*;
import md.model.SiteParser;

/**
 *
 * @author Hernan
 */
public abstract class AbstractSiteParserRegistry {

    public AbstractSiteParserRegistry() {
    }

    public Map<String, SiteParser> parsersMap() {
        Map<String, SiteParser> result = new LinkedHashMap<String, SiteParser>();
        for(SiteParser parser : listParsers()) {
            result.put(parser.getName(), parser);
        }
        return result;
    }

    public abstract List<SiteParser> listParsers();

}
