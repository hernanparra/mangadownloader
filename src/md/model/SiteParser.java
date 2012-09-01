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
package md.model;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author Hernan
 */
//TODO PARSERS Generar eventos desde los SiteParsers para los casos que requieran parsear muchas páginas
//TODO PARSERS Soportar problemas de comunicación con reintentos
public interface SiteParser {

    public String getName();
    public String getURL();
    public String getBaseURL();

    public List<MangaSeriesInfo> retrieveMangaList(String url, EventsHandler eh) throws IOException;

    public List<MangaChaptersInfo> retrieveMangaChaptersList(String url, EventsHandler eh) throws IOException;

    public MangaChapterInfo retrieveMangaPagesList(String url, EventsHandler eh) throws IOException;

}
