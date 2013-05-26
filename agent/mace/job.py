
import gnupg

class Job(object):
    def __init__(self, jobhash=None):
        """bar"""
        if jobhash:
            for key in jobhash.iteritems():
                self.append({key: jobhash[key]})

    def sign(self, userid, passphrase=None):
        """sign"""

    def submit(self):
        """submit"""

    def fetch(self, jobid=None):
        """fetch"""

    def verify(self):
        """verify"""

