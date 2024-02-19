package com.example.bulkupload.service;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ThumbnailExtractor {

    private static final String EXTENSION = "png";

    public static String getThumbnail(File source, String path, String filename) {
        try {
            FrameGrab frameGrab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(source));

            frameGrab.seekToSecondPrecise(0);

            Picture picture = frameGrab.getNativeFrame();

            BufferedImage bi = AWTUtil.toBufferedImage(picture);
            ImageIO.write(bi, EXTENSION, new File(path, filename + "." + EXTENSION));

            return filename + "." + EXTENSION;
        } catch (IOException | JCodecException e) {
            throw new RuntimeException(e);
        }
    }
}
