package com.shohidulhaque.my_people.common_model.data_transfer_object.base_model;

import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.error_codes.ErrorCode;
import com.shohidulhaque.my_people.common_model.sucess_and_error_codes.success_codes.SuccessCode;
import java.util.LinkedList;
import java.util.List;

public class ResponseFactory {

//    public static
//
//    <R, S, E>
//
//    DtoRequest<
//        Content<
//            R,
//            ContentSuccessResponse<ContentSuccessResponsePayload<S>>,
//            ContentErrorResponse<ContentErrorResponsePayload<E>>
//            >
//        >
//
//
//    GetResponse(R r, S s, E e){
//        List<
//            ContentSuccessResponse<ContentSuccessResponsePayload <S>>
//            >
//            contentSuccessResponses = new LinkedList<>();
//
//        List<
//            ContentErrorResponse<
//                    ContentErrorResponsePayload <E>
//                >
//            >
//            contentErrorResponses = new LinkedList<>();
//
//        Content<
//            R,
//            ContentSuccessResponse<ContentSuccessResponsePayload<S>>,
//            ContentErrorResponse<ContentErrorResponsePayload<E>>
//            > content = new Content<>(r, contentSuccessResponses, contentErrorResponses);
//
//        DtoRequest<
//            Content<
//                R,
//                ContentSuccessResponse<ContentSuccessResponsePayload<S>
//                    >,
//                ContentErrorResponse<
//                        ContentErrorResponsePayload<E>
//                    >
//                >
//            > dto = new DtoRequest<>(content);
//
//        return dto;
//    }
//
    public static

    <R, S, E>

    DtoRequest<
        Content<
            R,
            ContentSuccessResponse<SuccessCode.SuccessType,ContentSuccessResponsePayload<S>>,
            ContentErrorResponse<ErrorCode.ErrorType, ContentErrorResponsePayload<E>>
            >
        >


    GetResponse(R r, List<S> s, SuccessCode.SuccessType successType, List<E> e, ErrorCode.ErrorType errorType){
    List<
        ContentSuccessResponse<SuccessCode.SuccessType, ContentSuccessResponsePayload <S>>
        >
        contentSuccessResponses = new LinkedList<>();


        contentSuccessResponses.add(
            new ContentSuccessResponse<SuccessCode.SuccessType, ContentSuccessResponsePayload <S>>(
                successType,
                new ContentSuccessResponsePayload<S>(s)
            )
        );


    List<
        ContentErrorResponse<
            ErrorCode.ErrorType,
            ContentErrorResponsePayload <E>
            >
        >
        contentErrorResponses = new LinkedList<>();
        contentErrorResponses.add(
            new ContentErrorResponse<ErrorCode.ErrorType, ContentErrorResponsePayload <E>>(
                errorType,
                new ContentErrorResponsePayload<E>(e)
            )
        );

    Content<
        R,
        ContentSuccessResponse<SuccessCode.SuccessType,ContentSuccessResponsePayload<S>>,
        ContentErrorResponse<ErrorCode.ErrorType,ContentErrorResponsePayload<E>>
        > content = new Content<>(r, contentSuccessResponses, contentErrorResponses);

    DtoRequest<
        Content<
            R,
            ContentSuccessResponse<SuccessCode.SuccessType,ContentSuccessResponsePayload<S>
                >,
            ContentErrorResponse<
                ErrorCode.ErrorType,
                ContentErrorResponsePayload<E>
                >
            >
        > dto = new DtoRequest<>(content);

    return dto;
}

    public static void MAIN(){
        SuccessDescription<SuccessCode.SuccessType, String> success = new SuccessDescription<>(SuccessCode.SuccessType.general, "");
        List<SuccessDescription<SuccessCode.SuccessType, String>> successList = List.of(success);

        ErrorDescription<ErrorCode.ErrorType, String> error = new ErrorDescription<>(ErrorCode.ErrorType.general, "");
        List<ErrorDescription<ErrorCode.ErrorType, String>> errorList = List.of(error);

        var example =
            ResponseFactory.<
                    SuccessCode.SuccessType,
                    SuccessDescription<SuccessCode.SuccessType,
                        String>, ErrorDescription<ErrorCode.ErrorType, String>>GetResponse(SuccessCode.SuccessType.general,
                                                                                            successList,
                                                                                            SuccessCode.SuccessType.createChatSpace,
                                                                                            errorList,
                                                                                            null);

        System.out.println(example.getContent().getResponseType());
        example.getContent().getError().forEach( x -> {
            System.out.println(x.getResponseType());
            x.getPayload().getError().forEach(y -> {
                System.out.println(y.getDescription());
                System.out.println(y.getErrorType());
            });
        });

        example.getContent().getSuccess().forEach( g -> {
            System.out.println(g.getResponseType());
            g.getPayload().getSuccess().forEach( j -> {
                System.out.println(j.getDescription());
                System.out.println(j.getSuccessType());
            });
        });

    }
}
