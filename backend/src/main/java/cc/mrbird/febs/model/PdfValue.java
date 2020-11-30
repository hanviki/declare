package cc.mrbird.febs.model;

import lombok.Data;

@Data
public class PdfValue {
    PdfStyle pdfStyle;
    int colspan;
    int rowspan;
    String cellValue;
}
