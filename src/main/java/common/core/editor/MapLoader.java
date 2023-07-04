package common.core.editor;

import com.google.gson.Gson;
import common.core.objects.DynamicElement;
import common.core.objects.Layer;
import common.core.objects.Map;
import common.core.objects.StaticElement;
import common.core.util.SpriteLoader;

import java.io.FileReader;
import java.util.List;

public class MapLoader extends SpriteLoader {
    public MapLoader(String PathToMaps) {
        super(PathToMaps);
    }

    public void loadMap(int ID) {
        Gson gson = new Gson();
        try {
            FileReader reader = new FileReader(PathToMaps + ID + ".map");
            Map map = gson.fromJson(reader, Map.class);
            List<Layer> layers = map.getLAYERS();
            for (Layer layer : layers) {
                for (StaticElement element : layer.getStaticElements()) {
                    element.setImages(getSprite(element.getType()));
                }
                for (DynamicElement de : layer.getDynamicElements()) {
                    de.setImages(getSprite(de.getType()));
                    if (de.isLockedCharacter()) {
                        map.setLockedCharacter(de);
                    }
                }
            }
            map.init();
        } catch (Exception e) {
            System.out.println("Error reading Maps");
        }
    }
}