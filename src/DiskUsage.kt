package du

import java.io.File


class DiskUsage(files: List<String>) {
  private val filesList = files
  private val sizes = mutableListOf<Long>()

  fun getFileOrFolderSize(dir: File): Long {
    var size: Long = 0
    if (!dir.isFile) {
        for (file in dir.listFiles()!!) {
            size += if (file.isFile) {
                file.length()
            } else getFileOrFolderSize(file)
        }
    } else size += dir.length()
    return size
  }

  fun summarise(list: List<Long>): Long {
    var sum: Long = 0
    for (i in 0 until list.size) sum += list[i]
    return sum
  }

  fun makeOutputString(l: Long, readable: Boolean, si: Boolean): String {
    val suffix = listOf("B", "KB", "MB", "GB")
    val s: String
    var cur = l
    var i = 0
    val divisor = if (si) 1000 else 1024 // if option -si is on, then div by 1000, else div by 1024
    if (readable) {
        while (cur >= divisor && i < 4) {
            cur /= divisor
            i++
        }
        s = cur.toString() + " " + suffix[if (i < 4) i else 3]
    } else {
        cur /= divisor
        if (cur < 1) cur = 1 // ���� ������ ������ ���������, �� ��������� ����� (�� 1 ��)
        s = cur.toString()
    }
    return s
  }

  fun calcSizeAndWriteResult(readable: Boolean, summed: Boolean, si: Boolean): Int {
      if (filesList.isEmpty()) return 1 // �� ������ ����� ��� �������� ��������
      for (i in 0 until filesList.size) {
          val curFile = File(filesList[i])
          if (curFile.exists()) sizes.add(i, getFileOrFolderSize(curFile))
          else {
             println("Can't get file " + filesList[i])
             return 1
          }
      }
      if (!summed) { // ������� ������� ���� ������
          for (i in 0 until filesList.size) println(filesList[i] + " " + makeOutputString(sizes[i], readable, si))
      } else { // ������� ����� �������� ������
          println("Files summary size = " + makeOutputString(summarise(sizes), readable, si))
      }
      return 0
  }

}


