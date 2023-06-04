package com.company.ims.configuration.overrides;

import io.jmix.gridexportui.GridExportProperties;
import io.jmix.gridexportui.exporter.excel.AllRecordsExporter;
import io.jmix.gridexportui.exporter.excel.ExcelExporter;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.component.Table;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Primary
@Component("extended_ExcelExporter")
public class ExtendedExcelExport extends ExcelExporter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmm");

    private String fileNamePrefix = "file";

    public ExtendedExcelExport(GridExportProperties gridExportProperties, AllRecordsExporter allRecordsExporter) {
        super(gridExportProperties, allRecordsExporter);
    }

    public void setFileNamePrefix(String fileNamePrefix) {
        this.fileNamePrefix = fileNamePrefix;
    }

    private String generateFileName() {
        return fileNamePrefix + "_"
                + LocalDateTime.now(
                ZoneId.ofOffset("UTC", ZoneOffset.ofHours(10))
        ).format(FORMATTER);
    }

    protected String getFileName(Table<Object> table) {
        return generateFileName();
    }

    protected String getFileName(DataGrid<Object> dataGrid) {
        return generateFileName();
    }
}
