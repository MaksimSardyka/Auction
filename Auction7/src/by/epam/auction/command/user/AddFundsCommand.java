package by.epam.auction.command.user;

import java.math.BigDecimal;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.command.page.ViewPage;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.domain.Role;
import by.epam.auction.domain.User;
import by.epam.auction.service.UserService;
import by.epam.auction.service.exception.ServiceException;
import by.epam.auction.validator.ExtractorImpl;
import by.epam.auction.validator.exception.WrongInputException;
import by.epam.auction.validator.parser.AmountParser;

public class AddFundsCommand implements Command {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * Service to work with user entities.
	 */
	UserService service;

	/**
	 * Constructor.
	 * 
	 * @param service
	 *            Service to use to work with user entities.
	 */
	public AddFundsCommand(UserService service) {
		this.service = service;
	}

	/**
	 * Operation to perform on register.
	 * 
	 * @throws CommandException
	 */
	@Override
	public ViewPage execute(SessionRequestContent requestContent) {
		LOG.log(Level.DEBUG, "Perform " + CommandType.ADD_FUNDS);
		ViewPage viewPage = ViewPage.LOGIN_PAGE;
		User user = (User) requestContent.getSessionAttributeValue(ParsingValues.USER);
		if (null != user) {
			try {
				BigDecimal amount = new ExtractorImpl<BigDecimal>().extract(new AmountParser(), ParsingValues.AMOUNT,
						requestContent);

				user = service.addFunds(user.getId(), amount);
				if (null != user) {
					requestContent.insertRequestAttribute(ParsingValues.SUCCESS_MESSAGE,
							"Money was sucessfully transfered");
					requestContent.insertSessionAttribute(ParsingValues.USER, user);
					LOG.log(Level.DEBUG, "User account for user " + user.getLogin() + " was updated");
					if (user.getRole() == Role.USER) {
						LOG.log(Level.DEBUG, "User account " + user.getLogin() + " was updated");
					} else if (user.getRole() == Role.ADMINISTRATOR) {
						LOG.log(Level.DEBUG, "Administrator account was updated");
					}
				}

			} catch (WrongInputException e) {
				LOG.log(Level.ERROR, e);
			} catch (ServiceException e) {
				LOG.log(Level.ERROR, e);
				requestContent.insertRequestAttribute(ParsingValues.ERROR_MESSAGE,
						"Unable to update your account at the moment. Try again later.");
			}
			viewPage = CommandType.VIEW_USER_DATA.getCommand().execute(requestContent);
		}
		return viewPage;
	}
}