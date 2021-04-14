package com.shohidulhaque.my_people.common_model.deserialization_generics;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Rectangle extends Shape {
    @NonNull
    private int w;
    @NonNull private int h;
}
