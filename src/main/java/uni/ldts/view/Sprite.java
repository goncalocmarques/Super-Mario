package uni.ldts.view;import javax.imageio.ImageIO;import java.awt.*;import java.awt.image.BufferedImage;import java.io.File;import java.io.IOException;public class Sprite implements Drawable {    private final BufferedImage[] imgs;    private int selected;    /**     * Create sprite from a collection of images     * @param imgs collection of images     */    public Sprite(BufferedImage[] imgs) {        this.imgs = imgs;    }    /**     * Create sprite from a sprite sheet     * @param path path to file     * @param n number of imgs     * @param dx size-x of each img     * @param dy size-y of each img     */    public Sprite(String path, int n, int dx, int dy) throws IOException {        BufferedImage sheet = ImageIO.read(new File(path));        int cols = sheet.getWidth(null) / dx;        int rows = sheet.getHeight(null) / dy;        this.imgs = new BufferedImage[n];        for (int i=0; i<rows; i++) {            for (int j=0; j<cols; j++) {                if ((i+1)*j > n) break; // <- not enough space for n imgs                imgs[(i*cols)+j] = sheet.getSubimage(j*dx, i*dy, dx, dy);            }        }    }    /**     * Select a frame from the images     * @param frame frame to select     */    public void select(int frame) { this.selected = frame; }    public int getSelectedFrame() { return this.selected; }    public void selectNext() { selected = (selected+1) % imgs.length; }    // get list of frames    public BufferedImage[] getFrames() { return this.imgs; }    @Override    public void draw(int x, int y, Graphics2D g) {        // draw frame into the screen        g.drawImage(imgs[selected], x, y, null);    }}