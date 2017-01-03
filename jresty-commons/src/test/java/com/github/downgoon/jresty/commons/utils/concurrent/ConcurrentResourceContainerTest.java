package com.github.downgoon.jresty.commons.utils.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConcurrentResourceContainerTest {

	private static class ConnectionResource {

		private String threadName;

		public ConnectionResource(String threadName) {
			this.threadName = threadName;
		}

		@Override
		public String toString() {
			return threadName;
		}

	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testBuild() throws Exception {

		final AtomicInteger buildCount = new AtomicInteger(0);

		final ConcurrentResourceContainer<ConnectionResource> container = new ConcurrentResourceContainer<>(

		new ResourceLifecycle<ConnectionResource>() {

			@Override
			public ConnectionResource buildResource(String name) throws Exception {
				ConnectionResource resource = new ConnectionResource(Thread.currentThread().getName());
				Thread.sleep(10);
				buildCount.incrementAndGet();
				return resource;
			}

			@Override
			public void destoryResource(String name, ConnectionResource resource) throws Exception {

			}

		}

		);

		final ConnectionResource[] cr = new ConnectionResource[3];
		final Thread[] threads = new Thread[3];
		final Exception[] exceptions = new Exception[3];

		final CountDownLatch finishLatch = new CountDownLatch(3);

		threads[0] = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					cr[0] = container.addResource("master");
				} catch (Exception e) {
					exceptions[0] = e;
				} finally {
					finishLatch.countDown();
				}
			}
		}, "t-master-0");

		threads[1] = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					cr[1] = container.addResource("master");
				} catch (Exception e) {
					exceptions[1] = e;
				} finally {
					finishLatch.countDown();
				}
			}
		}, "t-master-1");

		threads[2] = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					cr[2] = container.addResource("slave");
				} catch (Exception e) {
					exceptions[2] = e;
				} finally {
					finishLatch.countDown();
				}
			}
		}, "t-slave-2");

		for (int i = 0; i < 3; i++) {
			threads[i].start();
		}

		finishLatch.await();

		Assert.assertEquals(2, buildCount.get()); // NOT 3
		Assert.assertTrue(cr[0] == cr[1]);
		Assert.assertFalse(cr[0] == cr[2]);

		for (int i = 0; i < 3; i++) {
			Assert.assertNull(exceptions[i]);
		}

		boolean inMaster = "t-master-0".equals(cr[0].toString()) || "t-master-1".equals(cr[0].toString());

		Assert.assertTrue(inMaster);
		Assert.assertEquals(cr[0].toString(), cr[1].toString());
		Assert.assertEquals("t-slave-2", cr[2].toString());
	}

}
