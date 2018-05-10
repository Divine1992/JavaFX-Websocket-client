package com.divine.model;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Message {
    private String id;
    @NonNull
    private String sender;
    @NonNull
    private String content;
    private Date date;
}
