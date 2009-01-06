/**
 * 
 */
package com.jakeapp.jake.ics.impl.mock;

import com.jakeapp.jake.ics.UserId;
import com.jakeapp.jake.ics.exceptions.NetworkException;
import com.jakeapp.jake.ics.exceptions.NoSuchUseridException;
import com.jakeapp.jake.ics.exceptions.NotLoggedInException;
import com.jakeapp.jake.ics.exceptions.OtherUserOfflineException;
import com.jakeapp.jake.ics.exceptions.TimeoutException;
import com.jakeapp.jake.ics.msgservice.IMessageReceiveListener;
import com.jakeapp.jake.ics.msgservice.IMsgService;
import com.jakeapp.jake.ics.users.IUsersService;

public class FriendsOnlyMsgService implements IMsgService {

	private IUsersService users;

	private IMsgService msg;

	public FriendsOnlyMsgService(IUsersService users, IMsgService msg) {
		this.users = users;
		this.msg = msg;
	}

	@Override
	public IMsgService getFriendMsgService() {
		return this;
	}

	@Override
	public void registerReceiveMessageListener(
			final IMessageReceiveListener receiveListener)
			throws NotLoggedInException {
		new FriendsOnlyReceiveFilter(receiveListener, this.users);
	}

	@Override
	public Boolean sendMessage(UserId to_userid, String content)
			throws NetworkException, NotLoggedInException, TimeoutException,
			NoSuchUseridException, OtherUserOfflineException {
		if (users.isFriend(to_userid)) {
			return msg.sendMessage(to_userid, content);
		} else {
			return false;
		}
	}
}