package com.redhat.tintin;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RagConfig {

    @Bean
    VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel).build();
    }

    @Bean
    CommandLineRunner ingestPdfs(VectorStore vectorStore) {
        return args -> {
            Resource[] pdfs = new PathMatchingResourcePatternResolver()
                    .getResources("classpath:tintin-docs/*.pdf");

            TokenTextSplitter splitter = TokenTextSplitter.builder()
                    .withChunkSize(200)
                    .withMinChunkSizeChars(50)
                    .build();

            List<Document> allChunks = new ArrayList<>();
            for (Resource pdf : pdfs) {
                List<Document> docs = new PagePdfDocumentReader(pdf).read();
                allChunks.addAll(splitter.apply(docs));
            }

            vectorStore.add(allChunks);
        };
    }
}
