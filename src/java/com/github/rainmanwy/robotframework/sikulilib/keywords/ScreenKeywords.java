package com.github.rainmanwy.robotframework.sikulilib.keywords;

import java.io.File;
import java.util.Map;
import java.util.HashMap;

import org.robotframework.javalib.annotation.*;

import com.github.rainmanwy.robotframework.sikulilib.exceptions.TimeoutException;
import com.github.rainmanwy.robotframework.sikulilib.exceptions.ScreenOperationException;
import com.github.rainmanwy.robotframework.sikulilib.utils.CaptureFolder;

import org.sikuli.script.*;

/**
 * Created by Wang Yang on 2015/8/19.
 */

@RobotKeywords
public class ScreenKeywords {

    private static double DEFAULT_TIMEOUT = 3.0;
    private final Screen screen = new Screen();
    private double timeout;
    private Map<String, Match> highlightMap = new HashMap<String, Match>();

    public ScreenKeywords() {
        timeout = DEFAULT_TIMEOUT;
    }

    @RobotKeyword("Set Sikuli timeout(seconds)")
    @ArgumentNames({"timeout"})
    public String setTimeout(String timeout) {
        double oldTimeout = this.timeout;
        this.timeout = Double.parseDouble(timeout);
        return Double.toString(oldTimeout);
    }

    @RobotKeyword("Add image path")
    @ArgumentNames({"path"})
    public boolean addImagePath(String path) {
        return ImagePath.add(path);
    }

    @RobotKeyword("Set folder for captured images")
    @ArgumentNames({"path"})
    public void setCaptureFolder(String path) {
        CaptureFolder.getInstance().setCaptureFolder(path);
    }   

    private Match wait(String image, String timeout) throws TimeoutException {
        try {
            Match match = screen.wait(image, Double.parseDouble(timeout));
            capture(match);
            return match;
        }
        catch(FindFailed e) {
            capture();
            throw new TimeoutException("Timeout happend, could not find "+image, e);
        }
    }

    private Match find(String image) {
        try {
            Match match = screen.find(image);
            capture(match);
            return match;
        } catch (FindFailed e) {
            System.out.println("Could not find " + image);
            return null;
        }
    }

    @RobotKeyword("Wait until image shown in screen")
    @ArgumentNames({"image", "timeout"})
    public void waitUntilScreenContain(String image, String timeout) throws TimeoutException {
        wait(image, timeout);
    }

    @RobotKeyword("Screen should contain image")
    @ArgumentNames({"image"})
    public void screenShouldContain(String image) throws ScreenOperationException {
        Match match = find(image);
        if (match == null) {
            throw new ScreenOperationException("Screen should contain "+image);
        }
    }

    @RobotKeyword("Screen should not contain image")
    @ArgumentNames({"image"})
    public void screenShouldNotContain(String image) throws ScreenOperationException {
        Match match = find(image);
        if (match != null) {
            throw new ScreenOperationException("Screen should not contain "+image);
        }
    }

    @RobotKeyword("Input text. Image could be empty")
    @ArgumentNames({"text","image="})
    public void inputText(String text, String image) throws Exception {
        System.out.println("Input Text:");
        System.out.println(text);        
        this.click(image);    
        int result = screen.type(text);
        if (result == 0) {
            throw new ScreenOperationException("Input text failed");
        }
    }
    
    @RobotKeywordOverload
    public void inputText(String text) throws Exception {
        System.out.println("Input Text:");
        System.out.println(text);
        int result = screen.type(text);
        if (result == 0) {
            throw new ScreenOperationException("Input text failed");
        }
    }

    @RobotKeyword("Paste text.Text could be Chinese Characters.Image could be empty.")
    @ArgumentNames({"text","image="})
    public void pasteText(String text, String image) throws Exception {
        System.out.println("Paste Text:");
        System.out.println(text);
        this.click(image);
        int result = screen.paste(text);
        if (result == 0) {
            throw new ScreenOperationException("Paste text failed");
        }
    }

    @RobotKeywordOverload
    public void pasteText(String text) throws Exception {
        System.out.println("Paste Text:");
        System.out.println(text);
        int result = screen.paste(text);
        if (result == 0) {
            throw new ScreenOperationException("Paste text failed");
        }
    }
    
    @RobotKeyword("Click image.if there is one image,click it; if there are two,click second image in first image.Second image can be null.")
    @ArgumentNames({"image","targetImage="})
    public void click(String image) throws Exception{
        wait(image, Double.toString(this.timeout));
        try {
            screen.click(image);
        }
        catch (FindFailed e) {
            capture();
            throw new ScreenOperationException("Click "+image+" failed"+e.getMessage(), e);
        }
    }

    @RobotKeywordOverload
    public void click(String image, String targetImage) throws Exception {
        Match match = wait(image, Double.toString(this.timeout));
        System.out.println(image + " is found!");
        match.click(targetImage);
        capture(match.find(targetImage));
    }


    @RobotKeyword("Double click image.if there is one image,click it; if there are two,click second image in first image.Second image can be null.")
    @ArgumentNames({"image","targetImage="})
    public void doubleClick(String image) throws Exception{
        wait(image, Double.toString(this.timeout));
        try {
            screen.doubleClick(image);
        }
        catch (FindFailed e) {
            throw new ScreenOperationException("Double click "+image+" failed"+e.getMessage(), e);
        }
    }

    @RobotKeywordOverload
    public void doubleClick(String image, String targetImage) throws Exception {
        Match match = wait(image, Double.toString(this.timeout));
        System.out.println(image + " is found!");
        match.doubleClick(targetImage);
        capture(match.find(targetImage));
    }
    
    @RobotKeyword("Right click image.if there is one image,click it; if there are two,click second image in first image.Second image can be null.")
    @ArgumentNames({"image","targetImage="})
    public void rightClick(String image) throws Exception{
        wait(image, Double.toString(this.timeout));
        try {
            screen.rightClick(image);
        }
        catch (FindFailed e) {
            throw new ScreenOperationException("Right click "+image+" failed"+e.getMessage(), e);
        }
    }

    @RobotKeywordOverload
    public void rightClick(String image, String targetImage) throws Exception {
        Match match = wait(image, Double.toString(this.timeout));
        System.out.println(image + " is found!");
        match.rightClick(targetImage);
        capture(match.find(targetImage));
    }


    private void capture() {
        ScreenImage image = screen.capture();
        String imagePath = image.save(CaptureFolder.getInstance().getCaptureFolder());
        System.out.println("*DEBUG* Saved path: "+imagePath);
        File file = new File(imagePath);
        String fileName = file.getName();
        System.out.println("*HTML* <img src='" + CaptureFolder.getInstance().getSubFolder() + "/" + fileName + "'/>");
    }

    private void capture(Region region) {
        ScreenImage image = screen.capture(region);
        String imagePath = image.save(CaptureFolder.getInstance().getCaptureFolder());
        System.out.println("*DEBUG* Saved path: "+imagePath);
        File file = new File(imagePath);
        String fileName = file.getName();
        System.out.println("*HTML* <img src='" + CaptureFolder.getInstance().getSubFolder() + "/" + fileName + "'/>");
    }

    @RobotKeyword("Capture whole screen")
    @ArgumentNames({})
    public void captureScreen(){
        capture();
    }

    @RobotKeyword("Highlight matched image")
    @ArgumentNames({"image"})
    public void highlight(String image) throws Exception{
        Match match = null;
        if (highlightMap.containsKey(image)==false) {
            match = screen.find(image);
            highlightMap.put(image, match);
            match.highlight();
            capture();
        } else {
            System.out.println("*WARN* "+image+" was already highlighted");
        }
    }

    @RobotKeyword("Clear highlight from screen")
    @ArgumentNames({"image"})
    public void clearHighlight(String image) {
        if (highlightMap.containsKey(image)) {
            Match match = highlightMap.get(image);
            match.highlight();
            highlightMap.remove(image);
        } else {
            System.out.println("*WARN* " + image + " was not highlighted before");
        }
    }

    @RobotKeyword("Clear all highlights from screen")
    @ArgumentNames({})
    public void clearAllHighlights() {
        for(Match match : highlightMap.values()) {
            match.highlight();
        }
        highlightMap.clear();
    }

    


}
