package us.paradyme.function;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Employee {

    private Long empNo;
    private Integer hashCode;
    private String payload;
    private Long timestamp;
}
