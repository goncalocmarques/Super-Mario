package uni.ldts.mario;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import uni.ldts.model.GameElement;
import uni.ldts.model.IElementType;
import uni.ldts.model.world.Entity;
import uni.ldts.model.world.Object;
import uni.ldts.model.world.World;
import uni.ldts.physics.AABB;
import uni.ldts.view.Drawable;
import uni.ldts.view.Sprite;
import uni.ldts.view.Texture;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;

public class WorldFactory {

    // path to images folder
    public static final String IMAGES_PATH;
    static {
        String s = File.separator;
        IMAGES_PATH = "src"+s+"main"+s+"resources"+s+"images"+s;
    }

    private World world;
    public World getWorld() {
        return this.world;
    }

    /**
     * Creates the world and sets the character
     * @param character player character
     * @return this instance
     */
    public WorldFactory setCharacter(Entity character) {
        this.world = new World(character);
        return this;
    }

    /**
     * Load mario world from .tmx file
     * @param path path to file
     * @return this instance
     */
    public WorldFactory loadWorld(String path) throws Exception {

        File file = new File(path);
        if (!file.exists()) throw new IllegalArgumentException();

        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
        Document doc = fac.newDocumentBuilder().parse(file); // parse .tmx file
        doc.getDocumentElement().normalize();

        Element root = doc.getDocumentElement();
        NodeList layer = root.getElementsByTagName("imagelayer");

        Node node = layer.item(0);
        // load background first
        this.loadBackground(node);

        NodeList groups = root.getElementsByTagName("objectgroup");
        Element aux = (Element)groups.item(0);
        NodeList objects = aux.getElementsByTagName("object");

        // load world elements
        for (int i = 0; i < objects.getLength(); i++) {
            node = objects.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) continue;
            this.loadElement((Element)node);
        }
        return this;
    }

    /**
     * Load world background from tmx file
     * @param node document node
     * @throws IOException invalid path
     */
    private void loadBackground(Node node) throws IOException {
        Node bgElement = ((Element)node).getElementsByTagName("image").item(0);
        String path = bgElement.getAttributes().getNamedItem("source").getTextContent();
        world.setBackground(new Texture(IMAGES_PATH + path));
    }

    /**
     * Load world element from tmx file
     * @param element document element
     */
    private void loadElement(Element element) throws ReflectiveOperationException, IOException {

        String name = element.getAttribute("name");
        String type = element.getAttribute("type");
        Drawable artwork = this.getArtwork(name);

        int x = Integer.parseInt(element.getAttribute("x"));
        int y = Integer.parseInt(element.getAttribute("y"));
        int dx = Integer.parseInt(element.getAttribute("width"));
        int dy = Integer.parseInt(element.getAttribute("height"));
        AABB aabb = new AABB(x, y, dx, dy);

        // build class using reflection
        Class<?> clazz = Class.forName("uni.ldts.mario.elements." + type);
        Constructor<?> c = clazz.getConstructor(Drawable.class, IElementType.class, AABB.class, boolean.class, boolean.class);
        GameElement gameElement = (GameElement) c.newInstance(artwork, null, aabb, false, false);

        // add element to the world
        if ((gameElement instanceof Entity)) world.getEntities().add((Entity)gameElement);
        else world.getObjects().add((Object)gameElement);
    }

    /**
     * Get artwork based on object name
     * @param name object name
     * @return object texture
     */
    private Drawable getArtwork(String name) throws IOException {
        return switch (name) {
            case "VerticalGreenBlock" -> new Texture(IMAGES_PATH + "green-vertical.png");
            case "WhiteSquare" -> new Texture(IMAGES_PATH + "glacier-square.png");
            case "PinkHorizontalBlock" -> new Texture(IMAGES_PATH + "pink-horizontal.png");
            case "BlueHorizontalBlock" -> new Texture(IMAGES_PATH + "blue-horizontal.png");
            case "GlacierVerticalBlock" -> new Texture(IMAGES_PATH + "glacier-vertical.png");
            case "BigBlueSquare" -> new Texture(IMAGES_PATH + "blue-square.png");
            case "Grass" -> new Texture(IMAGES_PATH + "grass-sm.png");
            case "RightGrass" -> new Texture(IMAGES_PATH + "grass-edge-right.png");
            case "LeftGrass" -> new Texture(IMAGES_PATH + "grass-edge-left.png");
            case "TexturedBlock" -> new Texture(IMAGES_PATH + "textured.png");
            case "TexturedPlatform" -> new Texture(IMAGES_PATH + "textured-platform.png");
            case "Flippers" -> new Texture(IMAGES_PATH + "flippers.png");
            case "TopPipe" -> new Texture(IMAGES_PATH + "top.png");
            case "MiddlePipe" -> new Texture(IMAGES_PATH + "middle.png");
            default -> null;
        };
    }
}
