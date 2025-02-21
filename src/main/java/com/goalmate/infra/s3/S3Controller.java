package com.goalmate.infra.s3;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.goalmate.api.FileApi;
import com.goalmate.support.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class S3Controller implements FileApi {
	private static final String FOLDER_NAME = "images";
	private final AmazonS3 amazonS3Client;

	@Value("${aws.s3.bucket-name}")
	private String bucketName;

	@Override
	public ResponseEntity uploadImages(List<MultipartFile> files) throws Exception {
		List<String> imageUrls = new ArrayList<>();

		for (MultipartFile file : files) {
			String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(file.getContentType());
			metadata.setContentLength(file.getSize());

			// S3에 파일 업로드
			amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));

			// 업로드된 파일의 URL 생성
			String fileUrl = amazonS3Client.getUrl(bucketName, fileName).toString();
			imageUrls.add(fileUrl);
		}

		return ResponseEntity.ok(ApiResponse.success(imageUrls));
	}
}
