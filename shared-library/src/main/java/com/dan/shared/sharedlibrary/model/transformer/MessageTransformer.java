package com.dan.shared.sharedlibrary.model.transformer;

public interface MessageTransformer<I, O> {

    O transform(I input);
    
}
