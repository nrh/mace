from nose.tools import assert_equal
from nose.tools import assert_not_equal
from nose.tools import assert_raises
from nose.tools import raises
import json
import os
import sys
from pprint import pformat

import mace.job
import mace.client

class TestInsertJob(object):
    @classmethod
    def setup_class(thing):
        """setup_class"""

    @classmethod
    def teardown_class(thing):
        """teardown_class"""

    def test_job_registration(self):
        """test_job_registration"""
        curdir = os.path.dirname(os.path.abspath(__file__))
        with open(os.sep.join([curdir, "job1.json"])) as f:
            js = json.load(f)
            job = mace.job.Job(js)
            job.sign('testuser', 'passphrase')
            res = job.submit()
            sys.stderr.write(pformat(res))


#    @raises(KeyError)
#    def
