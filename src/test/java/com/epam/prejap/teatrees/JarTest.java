package com.epam.prejap.teatrees;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

/**
 * Tests jar file
 *
 * @author Mariusz Bal
 */
@Test
public class JarTest {

    private File file;

    @BeforeClass
    public void setup() {
        file = getJarFile();
    }

    /**
     * Looks for the file of name starting with project name and having .jar extension
     *
     * @return the file if it meets naming requirements or {@code null} otherwise
     */
    private File getJarFile() {
        File[] files = new File("target").listFiles();
        return Arrays.stream(files)
                .filter(f -> f.getName().startsWith("tea-trees") && f.getName().endsWith(".jar"))
                .findAny()
                .orElse(null);
    }

    /**
     * Checks whether the jar file is properly created
     */
    public void shouldCreateJar() {
        Assert.assertTrue(file.exists(), "The jar file does not exist");
    }

    /**
     * Tests whether the jar file contains an entry containing proper main class FQN
     *
     * @throws IOException an exception related to opening the file
     */
    public void shouldContainCorrectMainClass() throws IOException {
        JarFile jarFile = new JarFile(file);
        String actualMain = jarFile.getManifest().getMainAttributes().getValue(Attributes.Name.MAIN_CLASS);
        String expectedMain = TeaTrees.class.getName();
        Assert.assertEquals(actualMain, expectedMain, "The actual main class FQN is not as expected");
    }
}
