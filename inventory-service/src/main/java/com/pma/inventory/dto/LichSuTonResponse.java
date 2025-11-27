package com.pma.inventory.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LichSuTonResponse {
    private Integer maLS;
    private String maThuoc;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime ngayCapNhat;
    
    private Integer soLuongThayDoi;
    private String lyDo;
}
