package com.shohidulhaque.my_people.common_model.deserialization_generics;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Circle extends Shape {
    @NonNull int radius;
}
