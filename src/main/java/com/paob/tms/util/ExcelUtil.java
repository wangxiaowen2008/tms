package com.paob.tms.util;

import com.paob.tms.model.TradeBlotter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class ExcelUtil {
    
    public static List<TradeBlotter> parseTradeBlotterFile(MultipartFile file) throws IOException {
        List<TradeBlotter> tradeBlotters = new ArrayList<>();
        
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                
                TradeBlotter tradeBlotter = new TradeBlotter();
                
                // 交易编号
                tradeBlotter.setTradeNumber(getStringValue(row.getCell(0)));
                
                // 交易日期
                Date tradeDate = getDateValue(row.getCell(1));
                if (tradeDate != null) {
                    tradeBlotter.setTradeDate(tradeDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                }
                
                // 交易时间
                Date tradeTime = getDateValue(row.getCell(2));
                if (tradeTime != null) {
                    tradeBlotter.setTradeTime(tradeTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
                }
                
                // 交易对手方
                tradeBlotter.setCounterpart(getStringValue(row.getCell(3)));
                
                // 买卖方向
                tradeBlotter.setBuySellBorrowLend(getStringValue(row.getCell(4)));
                
                // 货币
                tradeBlotter.setCurrency(getStringValue(row.getCell(5)));
                
                // 结算金额
                tradeBlotter.setSettlementAmount(getBigDecimalValue(row.getCell(6)));
                
                // 起息日
                Date valueDate = getDateValue(row.getCell(7));
                if (valueDate != null) {
                    tradeBlotter.setValueDate(valueDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                }
                
                // 到期日
                Date maturityDate = getDateValue(row.getCell(8));
                if (maturityDate != null) {
                    tradeBlotter.setMaturityDate(maturityDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                }
                
                // 备注
                tradeBlotter.setRemark(getStringValue(row.getCell(9)));
                
                tradeBlotters.add(tradeBlotter);
            }
        }
        
        return tradeBlotters;
    }
    
    private static String getStringValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }
    
    private static Date getDateValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        try {
            return cell.getDateCellValue();
        } catch (Exception e) {
            return null;
        }
    }
    
    private static BigDecimal getBigDecimalValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        try {
            return new BigDecimal(cell.getNumericCellValue());
        } catch (Exception e) {
            return null;
        }
    }
} 