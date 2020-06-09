package du

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.io.File

internal class DiskUsageTest {
    @Test
    fun getFileOrFolderSize() {
        val files = listOf<String>()
        var file = File("input/Test/test.bin") // Файл известного размера: 714503 байт
        var size = DiskUsage(files).getFileOrFolderSize(file)
        assertEquals(714503, size)
        file = File("input/Test") // Папка известного размера с вложенными папками: 13575265 байт
        size = DiskUsage(files).getFileOrFolderSize(file)
        assertEquals(13575265, size)
    }

    @Test
    fun summarise() {
        val list = listOf<Long>(0,1,2,3,4,5) // Сумма = 15
        val files = listOf<String>()
        val sum = DiskUsage(files).summarise(list)
        assertEquals(15, sum)
    }

    @Test
    fun makeOutputString() {
        val files = listOf<String>()
        var readable = false
        var si = false
        var number = 123456789L // Проверяем на константе
        var str = DiskUsage(files).makeOutputString(number, readable, si)
        assertEquals("120563", str)

        si = true
        str = DiskUsage(files).makeOutputString(number, readable, si)
        assertEquals("123456", str)

        si = false
        readable = true
        str = DiskUsage(files).makeOutputString(number, readable, si)
        assertEquals("117 MB", str)

        si = true
        str = DiskUsage(files).makeOutputString(number, readable, si)
        assertEquals("123 MB", str)

        // Дополнительно проверяем вывод в B, KB и GB
        readable = true
        number = 123L
        str = DiskUsage(files).makeOutputString(number, readable, si)
        assertEquals("123 B", str)
        number = 123456L
        str = DiskUsage(files).makeOutputString(number, readable, si)
        assertEquals("123 KB", str)
        number = 123456789000L
        str = DiskUsage(files).makeOutputString(number, readable, si)
        assertEquals("123 GB", str)
    }


    @Test
    fun calcSizeAndWriteResult() {
        val files1 = listOf<String>()
        var result = DiskUsage(files1).calcSizeAndWriteResult(readable = false, summed = false, si = false) // Список файлов пуст -> ошибка
        assertEquals(1, result)

        val files2 = listOf("input/Test") // Существующая папка -> успех
        result = DiskUsage(files2).calcSizeAndWriteResult(readable = false, summed = false, si = false) // Список файлов пуст -> ошибка
        assertEquals(0, result)
    }

}