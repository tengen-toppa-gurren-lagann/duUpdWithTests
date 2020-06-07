package du;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DuLauncherTest {

    @Test
    void launch1() {
        String[] cmdLine = {"-c", "-h", "c:\\nosuchfile.txt"}; // Такого файла нет -> проверяем, что result=1
        DuLauncher launcher = new DuLauncher();
        launcher.launch(cmdLine);
        assertEquals(1, launcher.result);
    }

    @Test
    void launch2() {
        String[] cmdLine = {"-c", "-h", "c:\\Test\\test.txt"}; // Такой файл есть -> проверяем, что result=0
        DuLauncher launcher = new DuLauncher();
        launcher.launch(cmdLine);
        assertEquals(0, launcher.result);
    }
}