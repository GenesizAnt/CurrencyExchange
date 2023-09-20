//package com.example.error;
//
//import jakarta.servlet.http.HttpServletResponse;
//
//public class ErrorQuery extends RuntimeException {
//
////    private String errorMessage;
////
////    public void sendError(int codeError, String msgError) {
////        response.setStatus(codeError);
////        errorMessage = msgError;
////    }
//
//
//    private String errorMessage;
//
//    public ErrorQuery(int codeError, String errorMessage, HttpServletResponse response) {
//        response.setStatus(codeError);
//        this.errorMessage = errorMessage;
//    }
//
//    public String getErrorMessage() {
//        return errorMessage;
//    }
//
//
//}
