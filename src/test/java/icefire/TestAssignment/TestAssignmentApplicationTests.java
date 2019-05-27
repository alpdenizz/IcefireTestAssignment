package icefire.TestAssignment;

import static org.junit.Assert.assertArrayEquals;

import org.apache.commons.codec.binary.Hex;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Ignore
public class TestAssignmentApplicationTests {

	@Test
	public void dummy() throws Exception {
		byte[] arr = {(byte)1, (byte)10, (byte)20, (byte)30, (byte)96};
		String str = Hex.encodeHexString(arr);
		byte[] obt = Hex.decodeHex(str);
		assertArrayEquals(arr, obt);
	}

}
