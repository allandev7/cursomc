package com.allan.cursomc.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.allan.cursomc.services.exception.FileException;

@Service
public class ImageService {
	public BufferedImage getJpgImageFromFile(MultipartFile multiPartFile) {
		String ext = FilenameUtils.getExtension(multiPartFile.getOriginalFilename());
		if(!"png".equals(ext) && !"jpg".equals(ext)) {
			throw new FileException("Somente imagens PNG e JPG são permitidas");
			
		}
		
		try {
			BufferedImage img = ImageIO.read(multiPartFile.getInputStream());
			if("png".equals(ext)) {
				pngToJpg(img);
			}
			return img;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new FileException("Erro ao ler arquivo");
		}
	}

	private BufferedImage pngToJpg(BufferedImage img) {
		BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		jpgImage.createGraphics().drawImage(img, 0, 0, Color.white, null);
		return jpgImage;
	}
	
	public InputStream getInputStream (BufferedImage img, String ext) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, ext, os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (IOException e) {
			// TODO: handle exception
			throw new FileException("Erro ao ler arquivo(IO)");
		}
	}
}
