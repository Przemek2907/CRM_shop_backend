package com.przemek.zochowski.service.dataFromFile;

import com.przemek.zochowski.dto.TradeDto;
import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.Trade;

import java.time.LocalDateTime;

public class TradeDtoParser implements Parser<TradeDto> {
    @Override
    public TradeDto parse(String text) {
            try {
                if (text == null ) {
                    throw new NullPointerException("NO DATA HAS BEEN FOUND FOR THE TRADE TABLE");
                }  else if (!text.matches("([A-Z]+\\s*)*")) {
                    throw new IllegalArgumentException("WRONG TRADE DATA FORMAT");
                }
                return TradeDto.builder().industry(text).build();
            } catch (Exception e) {
                throw new MyException(ErrorCode.FILE_PARSER, e.getMessage());
            }
        }
    }

