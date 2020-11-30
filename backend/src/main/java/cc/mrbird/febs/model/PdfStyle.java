package cc.mrbird.febs.model;

import lombok.Data;
import com.itextpdf.text.Font;
@Data
public class PdfStyle implements Cloneable {
    Font font;
    int border;
    int horizontalAlignment;
    int verticalAlignment;
    /**
     * 行高
     */
    float fixedHeight;
    float paddingLeft;
    float paddingRight;
    float paddingTop;
    float paddingBottom;


}
