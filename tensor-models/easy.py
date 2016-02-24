import tensorflow as tf
import numpy as np

g = tf.Graph()
with g.as_default():
	a = tf.placeholder(tf.float32, shape=[1, 1], name='a')
	b = tf.placeholder(tf.float32, shape=[1, 1], name='b')
	c = tf.mul(a, b, name="c")

	sess = tf.Session()
	init = tf.initialize_all_variables()
	sess.run(init)
	graph_def = g.as_graph_def()

	tf.train.write_graph(graph_def, 'models/', 'graph.pb', as_text=False)