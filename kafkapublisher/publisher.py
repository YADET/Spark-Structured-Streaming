import pathlib
import os
import sys
from confluent_kafka import Producer

FILE_PATH = pathlib.Path(__file__).absolute()
ROOT_PATH = FILE_PATH.parent.parent
sys.path.insert(0, ROOT_PATH.as_posix())
DATA_DIR =os.path.join(ROOT_PATH, "src/main/resources/data/nested.json")

p = Producer({'bootstrap.servers': 'localhost:9092'})

def delivery_report(err, msg):
    """ Called once for each message produced to indicate delivery result.
        Triggered by poll() or flush(). """
    if err is not None:
        print('Message delivery failed: {}'.format(err))
    else:
        print('Message delivered to {} [{}]'.format(msg.topic(), msg.partition()))

with open(DATA_DIR) as f:
    data = f.read()
    p.poll(0)
    p.produce('topic1', data.encode('utf-8'), callback=delivery_report)

p.flush()